import { useEffect, useState } from 'react';
import HeaderMain from './HeaderMain';     // full top header
import HeaderCompact from './HeaderCompact'; // small sticky header

export default function HeaderWrapper() {
  const [showCompact, setShowCompact] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      setShowCompact(window.scrollY > 150); // adjust threshold if needed
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <>
      {/* Main Header - visible only when not scrolled */}
      <div className={`${showCompact ? 'hidden' : ''}`}>
        <HeaderMain />
      </div>

      {/* Compact Header - appears after scroll */}
      <div
  className={`fixed top-0 left-0 right-0 z-50 transform transition-all duration-300 ${
    showCompact ? 'translate-y-0 opacity-100' : '-translate-y-full opacity-0'
  }`}
>
        <HeaderCompact />
      </div>
    </>
  );
}
